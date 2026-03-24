-- -- 已经获取锁的数量
-- local lockNum=0
-- -- 存入的地址，用作判断是否是当前用户
-- local ipAddr=ARGV[1]
-- -- 过期时间,单位秒
-- local expire=ARGV[2]

--key
local key = KEYS[1]
--value
local value = KEYS[2]
--过期时间
local time = ARGV[1]

redis.log(redis.LOG_NOTICE,'lock')
redis.log(redis.LOG_NOTICE,'key: '..key..', value: '..value..', time: '..time)

if redis.call('setnx', key, value) == 1
then
    redis.call('pexpire', key, time)
    return "true"
else
    return "false"
end