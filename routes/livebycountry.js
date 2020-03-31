const express = require("express");
const getLiveByCountry = require("../controllers/livebycountry");

router = express.Router();
router.post("/live", getLiveByCountry);
module.exports = router;
