insert into departments (id, name, description)
values
    (1, 'Администрация', 'Системное управление'),
    (2, 'Операционный отдел', 'Внутренние процессы'),
    (3, 'Маркетинг', 'Продвижение и реклама'),
    (4, 'HR', 'Подбор и адаптация сотрудников')
    on conflict (id) do nothing;

insert into users (id, name, email, password, role, department_id, is_active)
values
    (1, 'Администратор', 'admin@company.ru', '123456', 'ADMIN', 1, true),
    (2, 'Анна Смирнова', 'anna@company.ru', '123456', 'USER', 2, true),
    (3, 'Дмитрий Козлов', 'dmitry@company.ru', '123456', 'USER', 3, true),
    (4, 'Елена Орлова', 'elena@company.ru', '123456', 'USER', 4, true)
    on conflict (id) do nothing;