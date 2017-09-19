# encoding: utf-8
import urllib.parse

while True:
    text = input()
    if text.startswith("http"):
        isGet = True
        query = urllib.parse.urlparse(text).query
    else:
        isGet = False
        query = text

    for q in query.split("&"):
        k, v = [urllib.parse.unquote(x) for x in q.split("=")]
        print(f".{'param' if isGet else 'dataAsForm'}(\"{k}\" to \"{v}\")")
