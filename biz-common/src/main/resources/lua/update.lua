--key
local key = KEYS[1]
--value
local value = KEYS[2]
--过期时间
local time = ARGV[1]

--根据key判断是否有数据
local skuAmountStr = redis.call('get',key) --获取目前的库存量
if skuAmountStr == false then  --如果没有获取到返回false
    redis.log(redis.LOG_NOTICE,'AmountStr is null')
    return "false"
end


local sum= tonumber(skuAmountStr)+tonumber(value);
if tonumber(sum)<0 then
    redis.log(redis.LOG_NOTICE,'Inventory is negative')
    return "false"
end
--增减数据
redis.call('incrbyfloat',key,value)
if time~=""
    then
        redis.call('expire',key,time)--设置过期时间
    end
return "true"