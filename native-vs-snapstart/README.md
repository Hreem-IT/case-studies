# Introduction
With the newly released version of [SnapStar](https://aws.amazon.com/blogs/aws/new-accelerate-your-lambda-functions-with-lambda-snapstart/) we learned that we could get big performace benefits in cold-start times by snapshotting a warm-state of a java container and re-mounting that into the execution container.
I decided to try comparing the durations/latencies of a vanilla Jar, jar+snapstar and a GraalVM Native image. below are some of my findings.
* Disclaimer: I did use Quarkus for this which does add some possible overhead.


# Application architecture
![image](https://user-images.githubusercontent.com/10097082/204672522-eea9ca60-8d36-4a7e-aa2b-61cee9b035b0.png)

## Jar Deployment
![image](https://user-images.githubusercontent.com/10097082/204672787-6c062341-a37c-4cdb-a809-2a2a955fbfef.png)
Nothing unexpected here, we see some cold-starts taking 8-9 seconds, warmer lambdas however are able to serve the requests pretty quickly.

## Snapstart Deployment
![image](https://user-images.githubusercontent.com/10097082/204672763-b3a43ade-453e-4055-8fb1-ca33437be2f3.png)
We don't see row `coldstart=0` here because there are no cold-starts, instead AWS lambda takes a smaller amount of time ~200ms to restore a snapshot, called `Restore Duration`.
Something I notice up-front is that the snapstart jar version has some quite frequently occuring spikes in some of the requests, which can be seen as the `p99` metric.
This means that slowest 1% of the traffic took roughly 5 seconds to serve, which is still lower than the p50/p90/p99 of the vanilla jar variant.

## Native Deployment
![image](https://user-images.githubusercontent.com/10097082/204672907-ed75ec4e-c5e3-46f0-b30f-aafbb22cfd9f.png)
Going in to the test, I was actually hoping to see similar overall durations on snapstart as on native image, as even in native we have to account for those
small percentages of times when an actual cold-start will be required. We can however see that running on Substrate VM in a natively compiled binary is still
giving the best balance (for now) in terms of lowest possible cold-start times, as well as faster warm-state durations across the board.

# Conclusion
I do think that the infrequently occuring spikes in snapstar is probably a performance fix that AWS will amend in the future. If we thus disregard the p99 metric,
we can actually see that we are getting pretty comparable results to graalvm native image with a vanilla jar+snapstar configuration! 
This is good news as this means developers will get to write their lambdas in their favourite type-safe language, not have to worry about a long and tedious native compilation
phase, not have to worry about the code running in a close-world assumption with its included limits, and also get the benefits of much faster cold-start times!

All in all, looks very promising.
