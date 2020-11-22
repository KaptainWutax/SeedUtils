#!/usr/bin/env python
# -*- coding=utf-8 -*- pyversions=2.6+,3.3+
import json
try: #python3
    from urllib.request import urlopen
    from urllib.error import HTTPError, URLError
except: #python2
    from urllib2 import urlopen
    from urllib2 import HTTPError, URLError

import os
import sys
import datetime


def download_file(url, filename):
    try:
        print('Downloading '+filename+'...')
        f = urlopen(url)
        with open(filename, 'wb+') as local_file:
            local_file.write(f.read())
    except HTTPError as e:
        print('HTTP Error')
        print(e)
    except URLError as e:
        print('URL Error')
        print(e)


download_file("https://launchermeta.mojang.com/mc/game/version_manifest.json", "version_manifest.json")
if not os.path.isfile('version_manifest.json'):
    exit(-1)
print("Download of version_manifest.json was a success")
text = []
with open("version_manifest.json") as file:
    jq=json.load(file)
    versions = jq["versions"]
    typf = lambda typ: "SNAPSHOT" if typ == "snapshot" else "RELEASE" if typ == "release" else "OLD_ALPHA" \
        if typ == "old_alpha" else "OLD_BETA" if typ == "old_beta" else "NONE"
    convert_to_enum=lambda s:"v"+"_".join(s.split("."))
    major_minor=lambda s:(s.split(".")[1],s.split(".")[2] if len(s.split("."))==3 else "0")
    convert_to_time=lambda s:datetime.datetime.strptime(s.split("T")[0], '%Y-%m-%d').strftime("%B %m, %Y").replace(" 0", " ")
    last_major=major_minor(jq["latest"]["release"])[0]
    for i, version in enumerate(versions):
        if typf(version.get("type"))=="RELEASE":
            vid=version.get("id")
            vtime=version.get("releaseTime")
            major,minor=major_minor(vid)
            string_time=convert_to_time(vtime)
            enum_string=convert_to_enum(vid)
            print(f'{enum_string}("{vid}", {major}, {minor}), //{string_time}')
            if last_major!=major:
                print()
                last_major=major

print(";")
os.remove("version_manifest.json")