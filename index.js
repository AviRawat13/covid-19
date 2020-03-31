const express = require("express");
const getSummary = require("./routes/summary");
const app = express();

app.use("/api",getSummary);

const port = process.env.PORT || 8000;
app.listen(port, (req, res) => {
  console.log(`app running on ${port}`);
});
