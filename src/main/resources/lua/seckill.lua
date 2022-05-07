local userId = KEYS[1];
local goodsId = KEYS[2];
local stockNUmKey = goodsId..'_stockNum';
local orderKey = goodsId..'_'..userId;
--判断是否秒杀成功过
local orderExists = redis.call("get",orderKey);
if(orderExists and tonumber(orderExists)==1) then
    return 2;--用2表示已秒杀过
end
local num = redis.call("get",stockNUmKey);
if(num and tonumber(num)<1) then
    return 0;--用0表示库存不足
else
    redis.call("decr",stockNUmKey);
    redis.call("set",orderKey,1);
end
return 1;--秒杀成功
