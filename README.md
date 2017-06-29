# Example proto

```
import "validation.proto";


message HelloRequest {
    string name = 1;
    int32 age = 2 [(validation.Max) = 100, (validation.Min) = 18];
    repeated string hobbies = 3 [(validation.RepeatMax) = 5, (validation.RepeatMin) = 2];
    map<string, string> bagOfTricks = 4 [(validation.RepeatMax) = 10, (validation.RepeatMin) = 2];
    Sentiment sentiment = 5;
    int64 future_timemilles = 6 [(validation.Future) = true];
    int64 past_timemilles = 7 [(validation.Past) = true];
}
```

# Create grpc server

```java
  ManagedChannelBuilder builder = ManagedChannelBuilder.forAddress(url.getHost(), url.getPort()).usePlaintext(true);
  builder.intercept(new ValidationInterceptor());
```