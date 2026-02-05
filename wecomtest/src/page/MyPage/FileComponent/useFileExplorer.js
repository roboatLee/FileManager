import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import {
  getChildFiles,
  getDefaultFiles,
  getParentFiles,
  getContentTxt,
  getDisk,
} from "@/api/fileApi";
import { isDir, joinPath } from "@/page/MyPage/FileComponent/fileUtils";
import { printTree } from "../utils/fileTreePrinter";
import { getFavorites, addFavorite, deleteFavorite } from "@/api/favoriteApi";

export function useFileExplorer() {
  const router = useRouter();

  const current = ref({ path: "", files: [] });
  const selected = ref(null);
  const subtitle = ref("");
  const treeText = ref("");
  const disks = ref([]);
  const selectedDisk = ref("");
  const favorites = ref([]);

  onMounted(async () => {
    current.value = (await getDefaultFiles()).data;
    disks.value = (await getDisk()).data;
  });

  const open = async (item) => {
    if (!isDir(item)) return;
    current.value = (
      await getChildFiles(joinPath(current.value.path, item.name))
    ).data;
    selected.value = null;
  };

  const goParent = async () => {
    current.value = (await getParentFiles(current.value.path)).data;
    selected.value = null;
  };

  const switchDisk = async () => {
    if (!selectedDisk.value) return;
    current.value = (await getChildFiles(selectedDisk.value)).data;
    selected.value = null;
  };

  const parseJson = async () => {
    const file = current.value.files.find(
      (f) => f.name === selected.value && !isDir(f),
    );
    if (!file) return alert("请选择文件");

    subtitle.value = (await getContentTxt(current.value.path, file.name)).data;
  };

  const printStructure = async () => {
    treeText.value = (await printTree(current.value.path)).join("\n");
  };

  const openInWorkspace = () => {
    const file = current.value.files.find(
      (f) => f.name === selected.value && !isDir(f),
    );
    if (!file) return alert("请选择文件");

    router.push({
      name: "workspace",
      query: {
        path: current.value.path,
        name: file.name,
      },
    });
  };

  const isFavorite = (file) => {
    if (!file) return false;
    if (!Array.isArray(favorites.value)) return false;

    const fullPath = joinPath(current.value.path, file.name);
    return favorites.value.some((f) => f.path === fullPath);
  };

  const toggleFavorite = async (file) => {
    const fullPath = joinPath(current.value.path, file.name);
    const exist = favorites.value.find((f) => f.path === fullPath);

    if (exist) {
      await removeFavorite(exist.id);
      favorites.value = favorites.value.filter((f) => f.id !== exist.id);
    } else {
      const res = await addFavorite({
        path: fullPath,
        name: file.name,
      });
      favorites.value.push(res.data);
    }
  };

  return {
    current,
    selected,
    subtitle,
    treeText,
    disks,
    selectedDisk,
    favorites,
    open,
    goParent,
    switchDisk,
    parseJson,
    printStructure,
    openInWorkspace,
    isFavorite,
    toggleFavorite,
  };
}
