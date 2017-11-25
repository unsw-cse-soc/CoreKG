import Immutable from 'immutable';

const Client = Immutable.Record({
    id: null,
    name: '',
    secret: '',
    createdAt: null,
    updatedAt: null,
});

export default Client;