# QuestionControllerApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**addQuestion**](#addquestion) | **POST** /question/addQuestion | |
|[**getListQuestionVo**](#getlistquestionvo) | **GET** /question/list | |

# **addQuestion**
> addQuestion()


### Example

```typescript
import {
    QuestionControllerApi,
    Configuration,
    QuestionDto
} from './api';

const configuration = new Configuration();
const apiInstance = new QuestionControllerApi(configuration);

let questionDto: QuestionDto; // (default to undefined)
let authorization: string; // (default to undefined)

const { status, data } = await apiInstance.addQuestion(
    questionDto,
    authorization
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **questionDto** | **QuestionDto** |  | defaults to undefined|
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

# **getListQuestionVo**
> Array<object> getListQuestionVo()


### Example

```typescript
import {
    QuestionControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new QuestionControllerApi(configuration);

const { status, data } = await apiInstance.getListQuestionVo();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**Array<object>**

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

