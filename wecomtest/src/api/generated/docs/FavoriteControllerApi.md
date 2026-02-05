# FavoriteControllerApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**_delete**](#_delete) | **DELETE** /api/favorite/{id} | |
|[**add**](#add) | **POST** /api/favorite | |
|[**list**](#list) | **GET** /api/favorite | |

# **_delete**
> _delete()


### Example

```typescript
import {
    FavoriteControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new FavoriteControllerApi(configuration);

let id: string; // (default to undefined)

const { status, data } = await apiInstance._delete(
    id
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **id** | [**string**] |  | defaults to undefined|


### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **add**
> add(favoriteFolderDto)


### Example

```typescript
import {
    FavoriteControllerApi,
    Configuration,
    FavoriteFolderDto
} from './api';

const configuration = new Configuration();
const apiInstance = new FavoriteControllerApi(configuration);

let favoriteFolderDto: FavoriteFolderDto; //

const { status, data } = await apiInstance.add(
    favoriteFolderDto
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **favoriteFolderDto** | **FavoriteFolderDto**|  | |


### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **list**
> Array<FavoriteFolderDto> list()


### Example

```typescript
import {
    FavoriteControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new FavoriteControllerApi(configuration);

const { status, data } = await apiInstance.list();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**Array<FavoriteFolderDto>**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

