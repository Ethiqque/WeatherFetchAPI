import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './App.css';
import CityTable from './components/CityTable';

function App() {
    const [cities, setCities] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const [currentPage, setCurrentPage] = useState(0);
    const [citiesPerPage, setCitiesPerPage] = useState(15);

    const [searchTerm, setSearchTerm] = useState('');

    const [sortType, setSortType] = useState('name');
    const [sortOrder, setSortOrder] = useState('asc');

    const fetchCities = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/weather/cities');
            setCities(response.data);
            setLoading(false);
        } catch (error) {
            console.error('Error during the loading city data:', error);
            setError('Failed to load cities. Please try again later.');
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchCities();
    }, []);

    if (loading) {
        return <div>Loading data...</div>;
    }

    if (error) {
        return <div>{error}</div>;
    }

    return (
        <div>
            <h1>Current Temperature</h1>
            <CityTable
                cities={cities}
                currentPage={currentPage}
                setCurrentPage={setCurrentPage}
                citiesPerPage={citiesPerPage}
                setCitiesPerPage={setCitiesPerPage}
                searchTerm={searchTerm}
                setSearchTerm={setSearchTerm}
                sortType={sortType}
                setSortType={setSortType}
                sortOrder={sortOrder}
                setSortOrder={setSortOrder}
            />
        </div>
    );
}

export default App;
