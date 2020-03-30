const express = require("express")

const app=express()

app.get("/flagsdata",(req,res)=>{
    var data=require("./flag_data.json");
    console.log(data);  
})

const port = process.env.PORT || 8000
app.listen(port,(req,res)=>{
    console.log(`app running on ${port}`);
})