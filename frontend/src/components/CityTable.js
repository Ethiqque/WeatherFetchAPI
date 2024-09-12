import React, { useMemo, useCallback } from 'react';
import Pagination from './Pagination';
import { sortItems } from '../utils/sortUtils';

function CityTable({
                       cities,
                       currentPage,
                       setCurrentPage,
                       citiesPerPage,
                       setCitiesPerPage,
                       searchTerm,
                       setSearchTerm,
                       sortType,
                       setSortType,
                       sortOrder,
                       setSortOrder
                   }) {
    const indexOfLastCity = (currentPage + 1) * citiesPerPage;
    const indexOfFirstCity = indexOfLastCity - citiesPerPage;

    const getFilteredAndSortedCities = useCallback(() => {
        let filtered = cities.filter(city =>
            city.name.toLowerCase().includes(searchTerm.toLowerCase())
        );

        return filtered.sort((a, b) => sortItems(a, b, sortType, sortOrder));
    }, [cities, searchTerm, sortType, sortOrder]);

    const sortedCities = useMemo(getFilteredAndSortedCities, [getFilteredAndSortedCities]);
    const currentCities = sortedCities.slice(indexOfFirstCity, indexOfLastCity);
    const totalPages = Math.ceil(sortedCities.length / citiesPerPage);

    const handlePageSizeChange = (e) => {
        setCitiesPerPage(parseInt(e.target.value));
        setCurrentPage(0);
    };

    const changePage = (direction) => {
        setCurrentPage((prevPage) => {
            if (direction === 'next' && prevPage < totalPages - 1) {
                return prevPage + 1;
            } else if (direction === 'prev' && prevPage > 0) {
                return prevPage - 1;
            }
            return prevPage;
        });
    };

    const handleSearchChange = (event) => {
        setSearchTerm(event.target.value);
        setCurrentPage(0);
    };

    const handleSortChange = (type) => {
        if (sortType === type) {
            setSortOrder(sortOrder === 'asc' ? 'desc' : 'asc');
        } else {
            setSortType(type);
            setSortOrder('asc');
        }
    };

    return (
        <div>
            <div>
                <input
                    type="text"
                    placeholder="Search by name"
                    value={searchTerm}
                    onChange={handleSearchChange}
                />
            </div>

            <div>
                <label htmlFor="citiesPerPage">Cities on page: </label>
                <select id="citiesPerPage" value={citiesPerPage} onChange={handlePageSizeChange}>
                    <option value={15}>15</option>
                    <option value={25}>25</option>
                </select>
            </div>

            <div className="sorting-buttons">
                <button onClick={() => handleSortChange('name')}>
                    Sort by city ({sortOrder === 'asc' && sortType === 'name' ? 'A-Z' : 'Z-A'})
                </button>
                <button onClick={() => handleSortChange('temperature')}>
                    Sort by temperature ({sortOrder === 'asc' && sortType === 'temperature' ? '↑' : '↓'})
                </button>
            </div>

            <table>
                <thead>
                <tr>
                    <th>City</th>
                    <th>Temperature (°C)</th>
                </tr>
                </thead>
                <tbody>
                {currentCities.map((city) => (
                    <tr key={city.id}>
                        <td>{city.name}</td>
                        <td>{city.temperature} °C</td>
                    </tr>
                ))}
                </tbody>
            </table>

            <Pagination
                currentPage={currentPage}
                totalPages={totalPages}
                changePage={changePage}
            />
        </div>
    );
}

export default CityTable;
