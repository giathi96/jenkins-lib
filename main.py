# Your list of dictionaries
test = [
    {
        "sds": "dads",
        "name": "NGP",
        "id": "1"
    },
    {
        "name": "OAM",
        "id": "2"
    },
    {
        "name": "RADIO",
        "id": "3"
    }
]

# Your mapping
mapping = {
    "name": "Name",
    "id": "ID"
}

# Iterate through the list of dictionaries and rename the keys
result = []
for item in test:
    renamed_item = {mapping[key]: value for key, value in item.items()}
    result.append(renamed_item)

# The 'result' variable now contains the list of dictionaries with renamed keys
print(result)
