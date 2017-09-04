# encoding: utf-8
import json

int_min, int_max = -2 ** 31, 2 ** 31 - 1
long_min, long_max = -2 ** 63, 2 ** 63 - 1

def toLowerCamel(string):
    part = string.split("_")
    return part[0] + "".join([x.title() for x in part[1:]])

def toKotlinLiteral(obj):
    if isinstance(obj, str):
        return "\"{}\"".format(obj.replace("\n", "\\n"))
    elif isinstance(obj, bool):
        return "true" if obj else "false"
    elif isinstance(obj, (int, float)):
        return obj
    elif isinstance(obj, (list, dict)):
        return json.dumps(obj, ensure_ascii=False)
    elif not obj:
        return "null"
    else:
        return str(obj)


while True:
    text = ""
    try:
        while True:
            line = input()
            if line == " ":
                break
            text += line
    except KeyboardInterrupt:
        break

    try:
        jsonObj = json.loads(text)
    except json.decoder.JSONDecodeError as e:
        print(f"不正なJSONフォーマットです: {e}")
        continue
    
    result = []
    if isinstance(jsonObj, dict):
        for k, v in jsonObj.items():
            if isinstance(v, bool):
                method = "byNullableBool"
            elif isinstance(v, str):
                method = "byNullableString"
            elif isinstance(v, int):
                if int_min <= v <= int_max:
                    method = "byNullableInt"
                elif long_min <= v <= long_max:
                    method = "byNullableLong"
                else:
                    method = "byNullableBigInteger"
            elif isinstance(v, float):
                method = "byNullableFloat"
            elif isinstance(v, list):
                method = "byNullableArray"
            elif isinstance(v, dict):
                method = "byNullableObject"
            elif not v:
                method = "byNullableString"
            else:
                raise Exception(f"Unsupported type: {type(v).__name__}")
            
            name = toLowerCamel(k)
            if name == k:
                result.append([k, f"    val {name} by json.{method} // {toKotlinLiteral(v)}"])
            else:
                result.append([k, f"    val {name} by json.{method}(\"{k}\") // {toKotlinLiteral(v)}"])
    else:
        raise Exception("Only dict supported.")

    print("import com.google.gson.JsonElement\nimport com.github.salomonbrys.kotson.*\n\nclass UnnamedModel(json: JsonElement) {")
    for d in sorted(result, key=lambda x: x[0]):
        print(d[1])
    print("}")
