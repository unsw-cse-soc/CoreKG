import React from 'react';

const RenderSubmitButton = ({ label, id, name }) => {
    return (
        <div class="row">
            <div class="col s12 m12 l12">
                <button class="btn waves-effect waves-light" type="submit" name={name}>{label}
                </button>
            </div>
        </div>
    );
}
export default RenderSubmitButton;