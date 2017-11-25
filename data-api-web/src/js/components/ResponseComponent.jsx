import React from 'react';
import uniqueId from 'lodash/uniqueId';

const ResponseComponent = ({res}) => {
    const bodyComponent = Object.keys(res.body).map(key => <div class="row" key={uniqueId('prop_')}>
        <div class="col s12 m12 l12">
            {`${key}: ${res.body[key]}`}
        </div>
    </div >);
    return (
        <div class="col s12 m12 l12">
            <div class="card light-blue darken-1">
                <div class="card-content white-text">
                    <span class="card-title">Response</span>
                    {bodyComponent}
                </div>
            </div>
        </div>
    );
}
export default ResponseComponent;