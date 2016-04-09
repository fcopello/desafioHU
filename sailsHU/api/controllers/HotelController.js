/**
 * HotelController
 *
 * @description :: Server-side logic for managing hotels
 * @help        :: See http://sailsjs.org/#!/documentation/concepts/Controllers
 */

module.exports = {
	 search: function (req, res) {

	 	if (req.param('query')!=null) {

		 	Hotel.find({
			   or : [
			  {name : {'contains' : req.param('query')}},
			  {city : {'contains' : req.param('query')}}
			  ]
			
			}).exec(function createCB(err, data){
	  				
	  				return res.send(data);
			});
		} else if (req.param('city')!=null) {
			Hotel.find({
			  city : req.param('city')
			 }).exec(function createCB(err, data){
	  				var array = []
	  				data.forEach(function(item){
	  					array.push(item.id)
	  				});
	  				return res.send(array);
			});
		}
    
  	},
  	check: function (req, res) {

	 	if ((req.param('id')!=null || req.param('city')!=null) 
	 		&& req.param('startDate')!=null && req.param('endDate')!=null) {
			
			console.log("startDate: " + req.param('startDate'));
			console.log("endDate: " + req.param('endDate'));
			console.log("id: " + req.param('id'));
			console.log("city: " + req.param('city'));
			
			var startDate = new Date(req.param('startDate'));
			var endDate = new Date(req.param('endDate'));
			var diff = endDate-startDate;
			var days = Math.floor(diff/1000/60/60/24)+1;
			console.log("interval: " + days);

			function findDisp(ids, req, res) {
				Disp.find({where: {
		   		  hotelId : ids, 
				  date : { '>=': startDate, '<=': endDate },
				  available: true 
				
				}, sort: "hotelId"}).exec(function createCB(err, data){
						console.log("result: " + data);
						var array = mapReduceDisp(data, days);
		  				console.log(array);
		  				return findHotel(array, req, res);
		  				
				 });
			}

			function findHotel(array, req, res) {
				Hotel.find({
					  id : array
					 }).exec(function createCB(err, data){
					 	return res.send(data);
					 });
			}

			function mapReduceDisp(data, days) {
				var array = []
				var map = []
				var lastId = null;
				var i = 0;
				//Mapping and reducing total available
  				data.forEach(function(item){


  					if (item.hotelId!=lastId) {
  						
  						lastId = item.hotelId;
  						
  						if (map[0]!=null) {
  							array.push(map);
  							map = []
  						}
  						
  						map[0]=lastId;
  						map[1]=1;
  					} else {
  						map[1]+=1;
  					}

  					i++;
  					//Pushing the last element in array
  					if (i==data.length) {
  						array.push(map);
  					}
  					
  				});
  				//Filtering only results with all days available
  				array = array.filter(function(item) {
  					return item[1]>=days;
  				});
  				console.log("array: " + array);
  				//Adding filtered result into new array
  				var filtered = [];
  				array.forEach(function(item){
  					filtered.push(item[0])
  				});
  				console.log("filtered: " + filtered);

  				return filtered;
  				
			}

			if (req.param('id')!=null && req.param('id')!=undefined) {
				
				var id = req.param('id');

				return findDisp(id, req, res);

			} else {
				
				Hotel.find({
				  city : req.param('city')
				 }).exec(function createCB(err, data){

		  				var array = []

		  				data.forEach(function(item){
		  					array.push(item.id)
		  				});

		  				return findDisp(array, req, res);
		  				
				});
			}

			
		} else {
			return res.send("Wrong request");
		}
	},

    
  

    
  
	
};

