import Immutable from 'immutable';

const Token = Immutable.Record({
    accessToken: null,
    refreshToken: null,
    expiresIn: null,
});

export default Token;