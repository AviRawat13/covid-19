const https = require("https");

const liveByCountry = (req, res) => {
  // passing a call back to get data from API
//   covidSummary((data,error) => {
//     if (error){
//       return res.status(500).send(
//         {"message":"Could not fetch data. Please Try again"}
//       );
//     }

//     return res.json(data);
//   });
// };

return res.send(req.query.country);
}

var url = "https://api.covid19api.com/summary";

const getLiveData = callback => {
  https
    .get(url, function(res) {
      var body = "";
      res.on("data", function(chunk) {
        body += chunk;
      });

      res.on("end", function() {
        data = JSON.parse(body);
     
        return callback(data,undefined);
      });
    })
    .on("error", function(error) {
      console.log("Got an error: ", error);
      callback(undefined,error)
    });

};


module.exports = liveByCountry;
