import requests
from bs4 import BeautifulSoup

URL = "https://www.countryflags.io/"
response = requests.get(URL)
soup = BeautifulSoup(response.content, 'html.parser')

flag_divs = mydivs = soup.findAll("div", {"class": "item_country cell small-4 medium-2 large-2"})

data = dict()

for flag_div in flag_divs:
    paras = flag_div.find_all("p")
    data.update({
        paras[1].text:paras[0].text
    })

with open("flag_data.json","w") as f:
    import json
    f.write(json.dumps(data))
