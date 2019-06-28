import io
import urllib.request
import pandas as pd
import avro.schema
import avro.io
import decimal


def twos_comp(val, bits):
    """compute the 2's complement of int value val"""
    if (val & (1 << (bits - 1))) != 0: # if sign bit is set e.g., 8bit: 128-255
        val = val - (1 << bits)        # compute negative value
    return val                         # return positive value as is

def decimal_from_bytes(bytes, scale):
    original = int.from_bytes(bytes, byteorder='big', signed=False)
    bits = len(bytes)*8
    decoded = twos_comp(original, bits)
    return decimal.Decimal(decoded) / decimal.Decimal(10**scale)



url = "http://localhost:8080/api/users"
headers = {"Accept": "application/avro"}
request = urllib.request.Request(url, headers=headers)
response = urllib.request.urlopen(request)

schema = avro.schema.Parse(open("users-domain/src/main/avro/user.avsc", "r").read())

resp = response.read()
bytes_reader = io.BytesIO(resp)
decoder = avro.io.BinaryDecoder(bytes_reader)
reader = avro.io.DatumReader(schema)
user = reader.read(decoder)
print(user)

columns = user['salary_structure']
extended = ['place'] + columns
scale = user['salary_precision']
rows = []
for key, value in user['salaries'].items():
    numbers = []
    for val in value:
        numbers.append(decimal_from_bytes(val, scale))
    row = [key] + numbers
    rows.append(row)

df = pd.DataFrame(rows, columns=(extended))

print(df)