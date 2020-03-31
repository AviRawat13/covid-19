const express = require("express");
const getSummary = require("../controllers/summary");

router = express.Router();
router.get("/summary", getSummary);
module.exports = router;
