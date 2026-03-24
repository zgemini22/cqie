--key
local key = KEYS[1]
--value
local value = KEYS[2]

redis.log(redis.LOG_NOTICE,'unlock')
redis.log(redis.LOG_NOTICE,'key: '..key..', value: '..value)

local valueStr = redis.call('get',key)

if value ==  valueStr
then
    local del = redis.call('del',key)
    redis.log(redis.LOG_NOTICE,'key: '..key..', del: '..del)
    return "true"
else
    return "false"
end