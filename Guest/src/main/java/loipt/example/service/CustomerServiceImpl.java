package loipt.example.service;

import loipt.example.model.Customer;
import loipt.example.repository.CustomerRepository;
import loipt.example.repository.CustomerRepositoryImpl;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    
    private CustomerRepository customerRepository;
    
    public CustomerServiceImpl() {
        this.customerRepository = new CustomerRepositoryImpl();
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }
} 