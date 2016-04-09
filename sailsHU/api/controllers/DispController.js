/**
 * DispController
 *
 * @description :: Server-side logic for managing disps
 * @help        :: See http://sailsjs.org/#!/documentation/concepts/Controllers
 */

module.exports = {
	check: function (req, res) {

	 	if ((req.param('ids')!=null || req.param('city')!=null) 
	 		&& req.param('startDate')!=null && req.param('endDate')!=null) {
			
			var ids = null;
			var startDate = new Date(req.param('startDate'));
			var endDate = new Date(req.param('endDate'));

			function result (array, req, res) {
				Disp.find({
		   		  hotelId : array, 
				  date : { '>=': startDate, '<=': endDate } 
				
				}).exec(function createCB(err, data){
		  				
		  				return res.send(data);
				});
			}

			if (req.param('ids')!=null) {
				ids = req.param('ids').split(",");

				return result(ids, req, res);


			} else {

				Hotel.find({
				  city : req.param('city')
				 }).exec(function createCB(err, data){

		  				var array = []

		  				data.forEach(function(item){
		  					array.push(item.id)
		  				});
		  				
		  				return result(array, req, res);
				});
			}
			
		}
	},
};

