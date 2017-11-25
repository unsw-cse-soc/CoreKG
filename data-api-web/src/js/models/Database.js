import Immutable from 'immutable';

const Database = Immutable.Record({
    id: null,
    name: '',
    type: '',
    createdAt: null,
    updatedAt: null,
});

export default Database;