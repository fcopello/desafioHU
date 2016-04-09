/**
 * Bootstrap
 * (sails.config.bootstrap)
 *
 * An asynchronous bootstrap function that runs before your Sails app gets lifted.
 * This gives you an opportunity to set up your data model, run jobs, or perform some special logic.
 *
 * For more information on bootstrapping your app, check out:
 * http://sailsjs.org/#!/documentation/reference/sails.config/sails.config.bootstrap.html
 */

module.exports.bootstrap = function(cb) {

  	//Reading CSV and saving data
  	//using the sails ORM
  	sails.fastcsv = require('fast-csv');
  	
  	const fs = require('fs');
  	
  	//Removing all data
  	Hotel.destroy({}).exec(function (err) {});
  	Place.destroy({}).exec(function (err) {});
  	Disp.destroy({}).exec(function (err) {});
  
  	//Reading hotels
  	var streamHotel = fs.createReadStream("./artefatos/hoteis.csv");
 
	var csvHotelStream = sails.fastcsv()
	    .on("data", function(data){

	    	 Hotel.create({
	    	 	"id":data[0],
	    	 	"name":data[2],
	    	 	"city":data[1]
	    	 	}).exec(function createCB(err, created){
	    	 		if (created != null) {
						console.log('Created hotel with name ' + created.name);
					}
				});

	    })
	    .on("end", function(){
	         console.log("done");
	    });

	streamHotel.pipe(csvHotelStream);

	//Reading hotels
  	var streamPlace = fs.createReadStream("./artefatos/places.csv");
 
	var csvPlaceStream = sails.fastcsv()
	    .on("data", function(data){

	    	var type = data[0]!=0?2:1

	    	 Place.create({
	    	 	"key":data[0]!=null?data[0]:0,
	    	 	"name":data[1],
	    	 	"type":type,
	    	 	}).exec(function createCB(err, created){
	    	 		if (created != null) {
						console.log('Created place with name ' + created.name);
					}
				});

	    })
	    .on("end", function(){
	         console.log("done");
	    });

	streamPlace.pipe(csvPlaceStream);

	//Reading available
	var streamDisp = fs.createReadStream("./artefatos/disp.csv");

	var csvDispStream = sails.fastcsv()
	    .on("data", function(data){


	    	var dateParts = data[1].split('/');

	    	 //Importante Note ****
	    	 //Forcing year to 2016 with the hard code bellow 
	    	 //to allow consistents searchs
	    	 Disp.create({
	    	 	"hotelId":data[0],
	    	 	"date": new Date(2016,dateParts[1]-1, dateParts[0]),
	    	 	"available":data[2]==1?true:false
	    	 	}).exec(function createCB(err, created){
					if (created != null) {
						console.log('Created disp for hotelId ' + created.hotelId + ' at date ' + created.date);
					}
				});

	    })
	    .on("end", function(){
	         console.log("done");
	    });

	streamDisp.pipe(csvDispStream);

  	cb();

};
