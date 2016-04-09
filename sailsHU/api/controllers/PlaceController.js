/**
 * PlaceController
 *
 * @description :: Server-side logic for managing Places
 * @help        :: See http://sailsjs.org/#!/documentation/concepts/Controllers
 */

module.exports = {
	search: function (req, res) {

	 	if (req.param('query')!=null) {
	 		
		 	Place.find({
			  name : {'contains' : req.param('query')}
			}).exec(function createCB(err, data){
	  				
	  				return res.send(data);
			});
		} else {
			return res.send("Worng request");
		}
    
  	},
};

