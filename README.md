# Example proto

```
import "validation.proto";


message HelloRequest {
    string name = 1[(validation.regex) = ""];
    int32 age = 2 [(validation.max) = 100, (validation.min) = 18];
    repeated string hobbies = 3 [(validation.repeatMax) = 5, (validation.repeatMin) = 2];
    map<string, string> bagOfTricks = 4 [(validation.repeatMax) = 10, (validation.repeatMin) = 2];
    Sentiment sentiment = 5;
    int64 future_timemilles = 6 [(validation.future) = true];
    int64 past_timemilles = 7 [(validation.past) = true];
}
```

# Create grpc channel

```java
  ManagedChannelBuilder builder = ManagedChannelBuilder.forAddress(url.getHost(), url.getPort()).usePlaintext(true);
  builder.intercept(new ValidationInterceptor());
```
