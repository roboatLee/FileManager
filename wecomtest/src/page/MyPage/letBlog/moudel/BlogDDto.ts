export class ArticleDto {
  "title"?: string;
  "markdownContent"?: string;
  "htmlContent"?: string;

  constructor(title:string, markdownContent:string, htmlContent:string){
    this.title = title;
    this.htmlContent = htmlContent;
    this.markdownContent = markdownContent;
  }
}
