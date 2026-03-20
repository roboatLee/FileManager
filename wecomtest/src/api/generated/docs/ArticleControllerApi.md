# ArticleControllerApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**addArticle**](#addarticle) | **POST** /article/addArticle | |
|[**deleteArticleById**](#deletearticlebyid) | **DELETE** /article/delete/{id} | |
|[**getAllArtile**](#getallartile) | **GET** /article/getAllArticle | |
|[**getArticleById**](#getarticlebyid) | **GET** /article/detail/{id} | |

# **addArticle**
> addArticle(articleDto)


### Example

```typescript
import {
    ArticleControllerApi,
    Configuration,
    ArticleDto
} from './api';

const configuration = new Configuration();
const apiInstance = new ArticleControllerApi(configuration);

let authorization: string; // (default to undefined)
let articleDto: ArticleDto; //

const { status, data } = await apiInstance.addArticle(
    authorization,
    articleDto
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **articleDto** | **ArticleDto**|  | |
| **authorization** | [**string**] |  | defaults to undefined|


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

# **deleteArticleById**
> deleteArticleById()


### Example

```typescript
import {
    ArticleControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new ArticleControllerApi(configuration);

let id: number; // (default to undefined)
let authorization: string; // (default to undefined)

const { status, data } = await apiInstance.deleteArticleById(
    id,
    authorization
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **id** | [**number**] |  | defaults to undefined|
| **authorization** | [**string**] |  | defaults to undefined|


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

# **getAllArtile**
> Array<ArticleVo> getAllArtile()


### Example

```typescript
import {
    ArticleControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new ArticleControllerApi(configuration);

const { status, data } = await apiInstance.getAllArtile();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**Array<ArticleVo>**

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

# **getArticleById**
> ArticleDetailVo getArticleById()


### Example

```typescript
import {
    ArticleControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new ArticleControllerApi(configuration);

let id: number; // (default to undefined)

const { status, data } = await apiInstance.getArticleById(
    id
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **id** | [**number**] |  | defaults to undefined|


### Return type

**ArticleDetailVo**

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

