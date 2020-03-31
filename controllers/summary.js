const https = require("https");

const getSummary = (req, res) => {
  // passing a call back to get data from API
  covidSummary((data,error) => {
    if (error){
      console.log("here");
      return res.status(500).send(
        {"message":"Could not fetch data. Please Try again"}
      );
    }

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
        return callback(data,undefined);
      });
    })
    .on("error", function(error) {
      console.log("Got an error: ", error);
      callback(undefined,error)
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
