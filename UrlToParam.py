# encoding: utf-8
import urllib.parse

while True:
    url = urllib.parse.urlparse(input())

    for q in url.query.split("&"):
        k, v = [urllib.parse.unquote(x) for x in q.split("=")]
        print(f".param(\"{k}\" to \"{v}\")")
