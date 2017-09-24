# encoding: utf-8
import Util

while True:
    method = input("Method (Default: GET): ").upper() or "GET"
    url = input("URL (include params): ") or "https://api.twitter.com/1.1/users/show.json?screen_name=hytusx"
    data = input("Form data (Default: \"\"): ") or ""
    responseType = input("Response type (Default: Object): ") or "Object"

    Util.makeEndpoint(method, url, data, responseType)
