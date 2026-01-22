# JsonControllerApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**getChild**](#getchild) | **GET** /api/file/getchild | |
|[**getDefaultFiles**](#getdefaultfiles) | **GET** /api/file/getdefaultfiles | |
|[**getJsonFile2txt**](#getjsonfile2txt) | **GET** /api/file/getcontenttxt | |
|[**getParetn**](#getparetn) | **GET** /api/file/getparent | |

# **getChild**
> object getChild()


### Example

```typescript
import {
    JsonControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new JsonControllerApi(configuration);

let path: string; // (default to undefined)

const { status, data } = await apiInstance.getChild(
    path
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **path** | [**string**] |  | defaults to undefined|


### Return type

**object**

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

# **getDefaultFiles**
> OnePathFilesVo getDefaultFiles()


### Example

```typescript
import {
    JsonControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new JsonControllerApi(configuration);

const { status, data } = await apiInstance.getDefaultFiles();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**OnePathFilesVo**

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

# **getJsonFile2txt**
> string getJsonFile2txt()


### Example

```typescript
import {
    JsonControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new JsonControllerApi(configuration);

let path: string; // (default to undefined)
let name: string; // (default to undefined)

const { status, data } = await apiInstance.getJsonFile2txt(
    path,
    name
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **path** | [**string**] |  | defaults to undefined|
| **name** | [**string**] |  | defaults to undefined|


### Return type

**string**

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

# **getParetn**
> object getParetn()


### Example

```typescript
import {
    JsonControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new JsonControllerApi(configuration);

let path: string; // (default to undefined)

const { status, data } = await apiInstance.getParetn(
    path
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **path** | [**string**] |  | defaults to undefined|


### Return type

**object**

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

