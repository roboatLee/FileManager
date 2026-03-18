# ArticleControllerApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**addArticle**](#addarticle) | **POST** /article/addArticle | |
|[**getAllArtile**](#getallartile) | **GET** /article/getAllArticle | |

# **addArticle**
> addArticle(body)


### Example

```typescript
import {
    ArticleControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new ArticleControllerApi(configuration);

let body: object; //

const { status, data } = await apiInstance.addArticle(
    body
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **body** | **object**|  | |


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

