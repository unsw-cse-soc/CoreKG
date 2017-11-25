import Immutable from 'immutable';

const User = Immutable.Record({
    id: null,
    userName: '',
    role: '',
    createdAt: null,
    updatedAt: null,
});

export default User;