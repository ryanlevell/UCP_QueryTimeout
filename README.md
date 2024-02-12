# Example of why query timeout should be larger than abandon connection timeout

If `abandonConnectionTimeout` is triggered before `queryTimeout`, the connection will be closed causing 2 exceptions.
This will trigger a `QueryTimeoutException`, and a second `SQLRecoverableException` when `@Transactional` attempts a rollback.
