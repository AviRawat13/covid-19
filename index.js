const express = require("express");
const getSummary = require("./routes/summary");
const liveByCountry = require("./routes/livebycountry");
const app = express();

app.use("/api", getSummary);
app.use("/api", liveByCountry);
const port = process.env.PORT || 8000;
app.listen(port, (req, res) => {
  console.log(`app running on ${port}`);
});
