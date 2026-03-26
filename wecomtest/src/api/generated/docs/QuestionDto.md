# QuestionDto


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**title** | **string** |  | [optional] [default to undefined]
**type** | **string** |  | [optional] [default to undefined]
**categoryId** | **number** |  | [optional] [default to undefined]
**difficulty** | **number** |  | [optional] [default to undefined]
**_options** | [**Array&lt;OptionDTO&gt;**](OptionDTO.md) |  | [optional] [default to undefined]
**answer** | **object** |  | [optional] [default to undefined]
**analysis** | **string** |  | [optional] [default to undefined]
**tags** | **Array&lt;string&gt;** |  | [optional] [default to undefined]
**isPublic** | **boolean** |  | [optional] [default to undefined]

## Example

```typescript
import { QuestionDto } from './api';

const instance: QuestionDto = {
    title,
    type,
    categoryId,
    difficulty,
    _options,
    answer,
    analysis,
    tags,
    isPublic,
};
```

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)
