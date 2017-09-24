# coding=utf-8
import urllib.parse
from typing import List

def toLowerCamel(string: str) -> str:
    part = string.split("_")
    return part[0] + "".join([x.title() for x in part[1:]])

def makeEndpoint(method: str, _url: str, data: str, responseType: str) -> None:
    url = urllib.parse.urlparse(_url)

    startMethodName = False
    methodName = ""
    for segment in url.path.split("/"):
        if not startMethodName:
            try:
                float(segment)
                startMethodName = True
            except ValueError:
                pass
        else:
            methodName += segment.rstrip(".json").title()

    result: List[str] = [
        "",
        f"@{method}",
        f"fun {method.lower()}{methodName or 'Undefined'}(vararg options: Pair<String, String?>): Response{responseType}<Undefined> {{",
        "    return client.session.new()",
        f"        .url(\"{_url.split('?')[0]}\")"
    ]

    if method != "POST":
        query = url.query
        requestMethodName = "param"
        optionsRequestMethodName = "params"
    else:
        query = data
        requestMethodName = "dataAsForm"
        optionsRequestMethodName = "dataAsForm"
    for q in query.split("&"):
        k, v = [urllib.parse.unquote(x) for x in q.split("=")]
        result.append(f"        .{requestMethodName}(\"{k}\" to \"{v}\")")
    result.append(f"        .{optionsRequestMethodName}(*options)")
    result.append(f"        .{method.lower()}()")
    result.append(f"        .getResponse{responseType}()")
    result.append("}")
    result.append("")

    print("\n    ".join(result))
