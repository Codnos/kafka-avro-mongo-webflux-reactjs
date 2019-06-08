import io
import urllib.request
import json
import pandas as pd
import avro.schema

url = "http://localhost:8080/api/users"
headers = {"Accept": "application/avro"}
request = urllib.request.Request(url, headers=headers)
response = urllib.request.urlopen(request)

schema = avro.schema.Parse(open("/Users/ligasgr/wrk/kafka-avro-mongo-spark/users-domain/src/main/avro/user.avsc", "r").read())

resp = response.read()
bytes_reader = io.BytesIO(resp)
decoder = avro.io.BinaryDecoder(bytes_reader)
reader = avro.io.DatumReader(schema)
user = reader.read(decoder)
print(user)