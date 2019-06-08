import urllib.request
import json
import pandas as pd

url = "http://localhost:8080/api/users"
headers = {"Accept": "application/json"}
request = urllib.request.Request(url, headers=headers)
response = urllib.request.urlopen(request)
data = json.loads(response.read())
print(data)

record = data[0]
columns = record['salaryStructure']
extended = ['place'] + columns
rows = []
for key, value in record['salaries'].items():
    row = [key] + value
    rows.append(row)

df = pd.DataFrame(rows, columns=(extended))

print(df)
