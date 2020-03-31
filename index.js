const express = require("express");

const app = express();

app.get("/api/flaglinks", (req, res) => {
  var data = require("./flag_data.json");
  console.log(data);
  res.json(data);
});

const port = process.env.PORT || 8000;
app.listen(port, (req, res) => {
  console.log(`app running on ${port}`);
});
