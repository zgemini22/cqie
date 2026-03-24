-- 添加redis

--key
local key = KEYS[1]
-- 值
local value = KEYS[2]
-- 过期时间
local time =ARGV[1]

--根据key获取数据
local AmountStr = redis.call('get',key)
--判断是否有数据
if AmountStr == false then
    redis.call('set',key,value)--无则新增
    if time~=""
        then
             redis.call('expire',key,time)--设置过期时间
        end
    redis.log(redis.LOG_NOTICE,'AmountStr is new success ')
    return "true"
end
return "false"