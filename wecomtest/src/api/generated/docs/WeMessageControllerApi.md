# WeMessageControllerApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**getMessages**](#getmessages) | **GET** /chat/messages | |
|[**sendMessage**](#sendmessage) | **POST** /chat/send | |

# **getMessages**
> Array<string> getMessages()


### Example

```typescript
import {
    WeMessageControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new WeMessageControllerApi(configuration);

const { status, data } = await apiInstance.getMessages();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**Array<string>**

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

# **sendMessage**
> sendMessage()


### Example

```typescript
import {
    WeMessageControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new WeMessageControllerApi(configuration);

let msg: string; // (default to undefined)

const { status, data } = await apiInstance.sendMessage(
    msg
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **msg** | [**string**] |  | defaults to undefined|


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

