package loipt.example.repository;

import loipt.example.model.Customer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerRepositoryImpl implements CustomerRepository {
    
    private List<Customer> customers;
    
    public CustomerRepositoryImpl() {
        initializeSampleData();
    }

    private void initializeSampleData() {
        customers = new ArrayList<>();
        
        customers.add(new Customer("Mai Văn Hoàn", "1983-08-20", "Hà Nội", 
                "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=150&h=150&fit=crop&crop=face"));
        
        customers.add(new Customer("Nguyễn Văn Nam", "1983-08-21", "Bắc Giang", 
                "https://images.unsplash.com/photo-1494790108755-2616b612b786?w=150&h=150&fit=crop&crop=face"));
        
        customers.add(new Customer("Nguyễn Thái Hòa", "1983-08-22", "Nam Định", 
                "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=150&h=150&fit=crop&crop=face"));
        
        customers.add(new Customer("Trần Đăng Khoa", "1983-08-17", "Hà Tây", 
                "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=150&h=150&fit=crop&crop=face"));
        
        customers.add(new Customer("Nguyễn Đình Thi", "1983-08-19", "Hà Nội", 
                "https://images.unsplash.com/photo-1438761681033-6461ffad8d80?w=150&h=150&fit=crop&crop=face"));
    }
    
    @Override
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers);
    }

} 