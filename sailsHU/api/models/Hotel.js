/**
 * Hotel.js
 *
 * @description :: TODO: You might write a short summary of how this model works and what it represents here.
 * @docs        :: http://sailsjs.org/documentation/concepts/models-and-orm/models
 */

module.exports = {

  attributes: {
  	id: {
	    type: 'integer',
	    defaultsTo: null
	},
	name: {
	    type: 'string',
	    defaultsTo: null
	},
	city: {
	    type: 'string',
	    defaultsTo: null
	}
  }
};

