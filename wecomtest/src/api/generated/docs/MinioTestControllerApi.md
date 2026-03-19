# MinioTestControllerApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**upload**](#upload) | **POST** /minio/upload | |

# **upload**
> string upload()


### Example

```typescript
import {
    MinioTestControllerApi,
    Configuration,
    UploadRequest
} from './api';

const configuration = new Configuration();
const apiInstance = new MinioTestControllerApi(configuration);

let uploadRequest: UploadRequest; // (optional)

const { status, data } = await apiInstance.upload(
    uploadRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **uploadRequest** | **UploadRequest**|  | |


### Return type

**string**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

