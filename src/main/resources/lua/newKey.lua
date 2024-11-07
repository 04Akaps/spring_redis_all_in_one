local key1 = KEYS[1]
local key2 = KEYS[2]
local resultKey = KEYS[3]

-- key1과 key2의 값을 가져오고, 값이 없으면 0을 기본값으로 사용
local value1 = tonumber(redis.call('GET', key1) or 0)
local value2 = tonumber(redis.call('GET', key2) or 0)

-- 두 값을 더하고, 결과를 resultKey에 저장
local result = value1 + value2
redis.call('SET', resultKey, result)

-- 계산된 결과를 반환
return result