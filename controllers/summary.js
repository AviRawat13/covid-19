const https = require("https");

const getSummary = (req, res) => {
  // passing a call back to get data from API
  covidSummary(data => {
    return res.json(data);
  });
};

var url = "https://api.covid19api.com/summary";

const covidSummary = callback => {
  https
    .get(url, function(res) {
      var body = "";
      res.on("data", function(chunk) {
        body += chunk;
      });

      res.on("end", function() {
        data = JSON.parse(body);
        data = getAdditionalSummary(data);
        return callback(data);
      });
    })
    .on("error", function(e) {
      console.log("Got an error: ", e);
    });

};

const getAdditionalSummary = data => {
  const flag_data = require("../flag_data.json");

  data.Countries.forEach(country => {
    keys = Object.keys(flag_data);
    if (keys.includes(country.Country.trim())) {
      countryCode = flag_data[country.Country.trim()];
      country.flagURL = `https://www.countryflags.io/${countryCode}/flat/64.png`;
      country.code = countryCode;
    } else {
      country.flagURL = "";
      country.code = "";
    }
  });

  return data;
};
module.exports = getSummary;
