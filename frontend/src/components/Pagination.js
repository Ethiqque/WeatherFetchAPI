import React from 'react';

function Pagination({ currentPage, totalPages, changePage }) {
    return (
        <div className="pagination">
            <button onClick={() => changePage('prev')} disabled={currentPage === 0}>
                Back
            </button>
            <span>Page {currentPage + 1} of {totalPages}</span>
            <button onClick={() => changePage('next')} disabled={currentPage === totalPages - 1}>
                Next
            </button>
        </div>
    );
}

export default Pagination;
